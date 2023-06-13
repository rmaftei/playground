package main

import "fmt"

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

	sorted_numbers := bubble_sort(numbers[:])

	fmt.Println("After sort")

	fmt.Println(sorted_numbers)

	fmt.Println("Finished")
}

func bubble_sort(numbers []int) []int {

	sorted_numbers := make([]int, len(numbers))

	copy(sorted_numbers, numbers)

	list_size := len(sorted_numbers)

	for i := 0; i < list_size-1; i++ {
		for j := i + 1; j < list_size; j++ {
			if sorted_numbers[i] > sorted_numbers[j] {
				sorted_numbers[j], sorted_numbers[i] = sorted_numbers[i], sorted_numbers[j]
			}
		}
	}

	return sorted_numbers
}
