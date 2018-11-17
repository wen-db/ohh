/**
 * Copyright (C), 2011-2018, 微贷网.
 */
package test.ayst;

/**
 * @author 文得保 2018/8/30.
 */
public class People {
    private String nat;//国籍
    private String pet;//宠物
    private String cig;//香烟
    private String dir;//饮料
    private String col;//颜色
    private People lef;
    private People rig;

    public People getLef() {
        return lef;
    }

    public void setLef(People lef) {
        this.lef = lef;
    }

    public People getRig() {
        return rig;
    }

    public void setRig(People rig) {
        this.rig = rig;
    }

    public String getNat() {
        return nat;
    }

    public void setNat(String nat) {
        this.nat = nat;
    }

    public String getPet() {
        return pet;
    }

    public void setPet(String pet) {
        this.pet = pet;
    }

    public String getCig() {
        return cig;
    }

    public void setCig(String cig) {
        this.cig = cig;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getCol() {
        return col;
    }

    public void setCol(String col) {
        this.col = col;
    }
}
