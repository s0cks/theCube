package theCube.utils;

public enum OperatingSystem{
    LINUX("linux"), WINDOWS("windows"), OSX("osx");

    public final String name;

    private OperatingSystem(String name){
        this.name = name;
    }

    public static OperatingSystem current(){
        String name = System.getProperty("os.name").toLowerCase();
        if(name.contains("win")){
            return WINDOWS;
        } else if(name.contains("mac")){
            return OSX;
        } else{
            return LINUX;
        }
    }

    public static boolean isWindows(){
        return current() == WINDOWS;
    }

    public static boolean isLinux(){
        return current() == LINUX;
    }

    public static boolean isMac(){
        return current() == OSX;
    }

    public String version(){
        return System.getProperty("os.version");
    }
}