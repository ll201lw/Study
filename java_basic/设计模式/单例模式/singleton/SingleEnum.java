package com.bgy.design_pattern.singleton;

public enum SingleEnum {
    INSTANCE;


    /**
     * 移动零
     *
     * @param nums
     */
    public void moveZeroes(int[] nums) {
        int[] numsCopy = new int[nums.length];
        int left = 0;
        int right = nums.length - 1;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                numsCopy[left] = nums[i];
                //记录从左到右的位置
                left++;
            }
            if (nums[i] == 0) {
                numsCopy[right] = nums[i];
                //记录从右到左的位置
                right--;
            }
        }
        for (int m = 0; m < nums.length; m++) {
            nums[m] = numsCopy[m];
        }
    }
}
