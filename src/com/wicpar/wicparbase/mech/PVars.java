package com.wicpar.wicparbase.mech;

/**
 * Created by Frederic on 12/09/2015 at 15:12.
 */
public class PVars
{
	public static final String Appdata = (System.getProperty("os.name")).toUpperCase().contains("WIN") ? System.getenv("AppData") : System.getProperty("user.home") + "/Library/Application Support";
	public static final String GameID = "sinkingsimulator2";
	public static final String GameFolder = Appdata + "/." + GameID;
	public static final String BaseModsFolder = GameFolder + "/Plugins";
	public static final String Natives = GameFolder + "/Natives";
}
