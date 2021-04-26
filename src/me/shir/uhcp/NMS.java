package me.shir.uhcp;

public enum NMS
{
    V_1_7_R4("V_1_7_R4", 0, false);
    
    private boolean ver;
    private static NMS currentVer;
    
    private NMS(final String s, final int n, final boolean paramBoolean) {
        this.ver = paramBoolean;
    }
    
    public boolean canJoin() {
        return this.ver;
    }
    
    public static void set(final NMS paramNMS) {
        NMS.currentVer = paramNMS;
    }
    
    public static boolean is(final NMS paramNMS) {
        return NMS.currentVer == paramNMS;
    }
    
    public static NMS get() {
        return NMS.currentVer;
    }
}
