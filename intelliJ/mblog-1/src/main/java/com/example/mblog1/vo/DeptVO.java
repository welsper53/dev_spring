package com.example.mblog1.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data   // getter, setter 역할을 포함한다
@Builder    // 생성자 생성 역할
public class DeptVO {
    private int deptno = 0;
    private String dname = null;
    private String loc = null;
    private String filename = null;
    private String fileurl = null;

}
