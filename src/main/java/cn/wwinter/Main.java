package cn.wwinter;

import cn.wwinter.dataStruct.BSTTree;

import java.util.List;

/**
 * ClassName: ${NAME}
 * Package: cn.wwinter
 * Description:
 * Datetime: 2023/11/9
 * Author: zhangdd
 */// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        List<BSTTree> gen = BSTTree.gen(4);
        for (BSTTree tree : gen) {
            tree.sketch();
        }
    }
}