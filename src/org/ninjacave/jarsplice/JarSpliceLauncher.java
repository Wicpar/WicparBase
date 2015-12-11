package org.ninjacave.jarsplice;

import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by Frederic on 09/09/2015 at 21:10.
 */
public class JarSpliceLauncher
{
	public JarSpliceLauncher() throws Exception {
		File file = this.getCodeSourceLocation();
		String nativeDirectory = this.getNativeDirectory();
		String mainClass = "com.wicpar.wicparbase.Main";
		String vmArgs = "";

		try {
			this.extractNatives(file, nativeDirectory);
			ArrayList arguments = new ArrayList();
			String javaPath = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
			arguments.add(javaPath);
			StringTokenizer vmArgsToken = new StringTokenizer(vmArgs, " ");
			int count = vmArgsToken.countTokens();

			for(int processBuilder = 0; processBuilder < count; ++processBuilder) {
				arguments.add(vmArgsToken.nextToken());
			}

			arguments.add("-cp");
			arguments.add(file.getAbsoluteFile().toString());
			arguments.add("-Djava.library.path=" + nativeDirectory);
			arguments.add(mainClass);
			ProcessBuilder var14 = new ProcessBuilder(arguments);
			var14.redirectErrorStream(true);
			Process process = var14.start();
			this.writeConsoleOutput(process);
			process.waitFor();
		} finally {
			this.deleteNativeDirectory(nativeDirectory);
		}
	}

	public void writeConsoleOutput(Process process) throws Exception {
		InputStream is = process.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);

		String line;
		while((line = br.readLine()) != null) {
			System.out.println(line);
		}

	}

	public void extractNatives(File file, String nativeDirectory) throws Exception {
		JarFile jarFile = new JarFile(file, false);
		Enumeration entities = jarFile.entries();

		while(true) {
			JarEntry entry;
			do {
				do {
					do {
						if(!entities.hasMoreElements()) {
							jarFile.close();
							return;
						}

						entry = (JarEntry)entities.nextElement();
					} while(entry.isDirectory());
				} while(entry.getName().indexOf(47) != -1);
			} while(!this.isNativeFile(entry.getName()));

			InputStream in = jarFile.getInputStream(jarFile.getEntry(entry.getName()));
			FileOutputStream out = new FileOutputStream(nativeDirectory + File.separator + entry.getName());
			byte[] buffer = new byte[65536];

			int bufferSize;
			while((bufferSize = in.read(buffer, 0, buffer.length)) != -1) {
				out.write(buffer, 0, bufferSize);
			}

			in.close();
			out.close();
		}
	}

	public boolean isNativeFile(String entryName) {
		String osName = System.getProperty("os.name");
		String name = entryName.toLowerCase();
		if(osName.startsWith("Win")) {
			if(name.endsWith(".dll")) {
				return true;
			}
		} else if(osName.startsWith("Linux")) {
			if(name.endsWith(".so")) {
				return true;
			}
		} else if((osName.startsWith("Mac") || osName.startsWith("Darwin")) && (name.endsWith(".jnilib") || name.endsWith(".dylib"))) {
			return true;
		}

		return false;
	}

	public String getNativeDirectory() {
		String nativeDir = System.getProperty("deployment.user.cachedir");
		if(nativeDir == null || System.getProperty("os.name").startsWith("Win")) {
			nativeDir = System.getProperty("java.io.tmpdir");
		}

		nativeDir = nativeDir + File.separator + "natives" + (new Random()).nextInt();
		File dir = new File(nativeDir);
		if(!dir.exists()) {
			dir.mkdirs();
		}

		return nativeDir;
	}

	public void deleteNativeDirectory(String directoryName) {
		File directory = new File(directoryName);
		File[] files = directory.listFiles();
		File[] var7 = files;
		int var6 = files.length;

		for(int var5 = 0; var5 < var6; ++var5) {
			File file = var7[var5];
			file.delete();
		}

		directory.delete();
	}

	public File getCodeSourceLocation() {
		try {
			return new File(JarSpliceLauncher.class.getProtectionDomain().getCodeSource().getLocation().toURI());
		} catch (URISyntaxException var2) {
			var2.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) throws Exception {
		new JarSpliceLauncher();
	}
}
