package com.sln.ontime.service.taskSorting;

import com.sln.ontime.model.po.Task;

import java.util.*;

/**
 * @Class TaskSortService
 * @Description 对一系列子任务进行排序
 * @Author Naren
 * @Date 2020/5/10 13:00
 * @Version 1.0
 */
public class TaskSortService {

    /**
     * 短作业优先
     * @return
     */
    public static class ShortFirst implements Comparator<Task>{

        @Override
        public int compare(Task o1, Task o2) {

            if( o1.getLasting() > o2.getLasting() ) {
                return 1;
            }else if( o1.getLasting().equals(o2.getLasting()) ){
                if( Long.valueOf(o1.getEndTime().replaceAll("[^0-9]",""))
                        > Long.valueOf(o2.getEndTime().replaceAll("[^0-9]","")) ){
                    return 1;
                }else{
                    return -1;
                }
            }else{
                return -1;
            }
        }
    }

    /**
     * 长作业优先
     * @return
     */
    public static class LongFirst implements Comparator<Task>{
        @Override
        public int compare(Task o1, Task o2) {

            if( o1.getLasting() < o2.getLasting() ) {
                return 1;
            }else if( o1.getLasting().equals(o2.getLasting()) ){
                if( Long.valueOf(o1.getEndTime().replaceAll("[^0-9]",""))
                        > Long.valueOf(o2.getEndTime().replaceAll("[^0-9]","")) ){
                    return 1;
                }else{
                    return -1;
                }
            }else{
                return -1;
            }
        }
    }

    /**
     * 高紧急度优先
     */
    public static class Priority implements Comparator<Task>{
        @Override
        public int compare(Task o1, Task o2) {

            if( o1.getPriority() < o2.getPriority()) {
                    return 1;
            }else if(  Long.valueOf( o1.getEndTime().replaceAll("[^0-9]","") )
                    .equals(
                            Long.valueOf( o2.getEndTime().replaceAll("[^0-9]","")))){
                if(o1.getLasting() < o2.getLasting()){
                    return 1;
                }else{
                    return -1;
                }
            }else{
                return -1;
            }
        }
    }

}
