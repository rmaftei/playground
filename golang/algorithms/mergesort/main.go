package main

import (
	"fmt"
)

func main() {
	numbers := [100]int{
		82, 2, 35, 86, 51, 27, 50, 57, 89, 17, 30, 67,
		2, 57, 22, 60, 5, 96, 26, 26, 51, 44, 69, 34,
		87, 79, 13, 63, 25, 39, 13, 59, 86, 75, 31,
		84, 42, 59, 43, 10, 68, 64, 91, 39, 52, 11,
		27, 25, 65, 99, 70, 39, 54, 21, 40, 39, 48,
		35, 68, 32, 94, 15, 40, 53, 62, 94, 70, 97,
		86, 17, 97, 82, 52, 98, 68, 50, 66, 6, 64,
		92, 10, 15, 81, 11, 45, 95, 96, 68, 21, 44,
		1, 42, 6, 73, 87, 25, 36, 68, 94, 23}

	fmt.Println("Before sort")

	fmt.Println(numbers)

	sorted_numbers := merge_sort(numbers[:])

	fmt.Println("After sort")

	fmt.Println(sorted_numbers)

	fmt.Println("Finished")
}

func merge_sort(numbers []int) []int {

	sorted_numbers := make([]int, len(numbers))

	copy(sorted_numbers, numbers)

	return merge_sort_internal(sorted_numbers)
}

func merge_sort_internal(numbers []int) []int {

	if len(numbers) > 1 {

		middle := len(numbers) / 2

		left_split := numbers[:middle]
		right_split := numbers[middle:]

		left := make([]int, len(left_split))
		right := make([]int, len(right_split))

		copy(left, left_split)
		copy(right, right_split)

		merge_sort_internal(left)
		merge_sort_internal(right)

		left_index := 0
		right_index := 0
		merge_index := 0

		for left_index < len(left) && right_index < len(right) {
			if left[left_index] < right[right_index] {
				numbers[merge_index] = left[left_index]
				left_index = left_index + 1

			} else {
				numbers[merge_index] = right[right_index]
				right_index = right_index + 1
			}

			merge_index = merge_index + 1
		}

		for left_index < len(left) {
			numbers[merge_index] = left[left_index]

			left_index = left_index + 1
			merge_index = merge_index + 1
		}

		for right_index < len(right) {
			numbers[merge_index] = right[right_index]

			right_index = right_index + 1
			merge_index = merge_index + 1
		}

	}

	return numbers
}
