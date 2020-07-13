package com.affaris.common.to;

/**
 * 退选课程所需数据的To
 *
 * @author Vulgarities
 */
public class AbortCourseTo {
    private Integer stuId;
    private Integer couId;

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

    @Override
    public String toString() {
        return "DropTo{" +
                "stuId=" + stuId +
                ", couId=" + couId +
                '}';
    }
}
