package com.affaris.common.to;

/**
 * @author Vulgarities
 */
public class DropTo {
    private Integer stuId;
    private Integer couId;

    @Override
    public String toString() {
        return "DropTo{" +
                "stuId=" + stuId +
                ", couId=" + couId +
                '}';
    }

    public Integer getStuId() {
        return stuId;
    }

    public void setStuId(Integer stuId) {
        this.stuId = stuId;
    }

    public Integer getCouId() {
        return couId;
    }

    public void setCouId(Integer couId) {
        this.couId = couId;
    }
}
