package com.wingsiwoo.www.entity;

import lombok.Data;

/**
 * @author WingsiWoo
 * @date 2021/10/14
 */
@Data
public class Coordinate {
    private Integer x;
    private Integer y;

    public Coordinate(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }
}
