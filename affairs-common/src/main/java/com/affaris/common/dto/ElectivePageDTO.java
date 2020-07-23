package com.affaris.common.dto;

/**
 * 获取选课表分页信息远程调用的To
 *
 * @author Vulgarities
 */
public class ElectivePageDTO {
    private Integer stuId;
    private Long current;

    @Override
    public String toString() {
        return "ElectivePageTo{" +
                "stuId=" + stuId +
                ", current=" + current +
                '}';
    }

    public Integer getStuId() {
        return stuId;
    }

    public void setStuId(Integer stuId) {
        this.stuId = stuId;
    }

    public Long getCurrent() {
        return current;
    }

    public void setCurrent(Long current) {
        this.current = current;
    }
}
