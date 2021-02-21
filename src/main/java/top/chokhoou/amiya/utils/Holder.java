package top.chokhoou.amiya.utils;

import lombok.Data;

@Data
public class Holder<E> {
    private volatile E data;
}
