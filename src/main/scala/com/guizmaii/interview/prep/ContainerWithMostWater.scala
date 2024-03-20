package com.guizmaii.interview.prep

/**
 * 11. Container With Most Water
 *
 * You are given an integer array height of length n. There are n vertical lines drawn such that the two endpoints of the ith line are (i, 0) and (i, height[i]).
 *
 * Find two lines that together with the x-axis form a container, such that the container contains the most water.
 *
 * Return the maximum amount of water a container can store.
 *
 * Notice that you may not slant the container.
 *
 * Example 1:
 *
 * Input: height = [1,8,6,2,5,4,8,3,7]
 * Output: 49
 * Explanation: The above vertical lines are represented by array [1,8,6,2,5,4,8,3,7]. In this case, the max area of water (blue section) the container can contain is 49.
 * Example 2:
 *
 * Input: height = [1,1]
 * Output: 1
 *
 * Constraints:
 *
 * n == height.length
 * 2 <= n <= 105
 * 0 <= height[i] <= 104
 */
object ContainerWithMostWater extends App {
  def maxArea(height: Array[Int]): Int = {
    var best  = 0
    var left  = 0
    var right = height.length - 1

    while (right > left) {
      val length = right - left
      val width  = math.min(height(left), height(right))
      val area   = width * length
      if (area > best) best = area
      if (height(left) < height(right)) left += 1 else right -= 1
    }

    best
  }

  val r0 = maxArea(Array(1, 8, 6, 2, 5, 4, 8, 3, 7))
  println(r0) // 49

  val r1 = maxArea(Array(1, 1))
  println(r1) // 1

  val r2 = maxArea(Array(1, 2, 1))
  println(r2) // 2
}
