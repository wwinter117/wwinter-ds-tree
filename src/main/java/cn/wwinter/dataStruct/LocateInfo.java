package cn.wwinter.dataStruct;

/**
 * @author: zhangdd
 * @date: 2023/07/26
 */
public  class LocateInfo<T> {
    private T date;
    private int level;
    private int locate;
    private int flag;

    public LocateInfo() {
    }

    public LocateInfo(T date, int level, int locate) {
        this.date = date;
        this.level = level;
        this.locate = locate;
    }
}
