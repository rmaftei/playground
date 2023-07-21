package main

import (
	"fmt"
)

func main() {
	numbers := []int{
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

	sorted_numbers := shell_sort(numbers)

	fmt.Println("After sort")

	fmt.Println(sorted_numbers)

	fmt.Println("Finished")
}

func shell_sort(numbers []int) []int {

	sorted_numbers := make([]int, len(numbers))

	copy(sorted_numbers, numbers)

	interval := 1

	// knuth formula
	for interval <= len(sorted_numbers)/3 {
		interval = interval*3 + 1
	}

	for interval > 0 {

		for outer := interval; outer < len(sorted_numbers); outer++ {
			next_elem := sorted_numbers[outer]

			inner := outer

			for inner > interval-1 && sorted_numbers[inner-interval] >= next_elem {
				sorted_numbers[inner] = sorted_numbers[inner-interval]

				inner = inner - interval
			}

			sorted_numbers[inner] = next_elem

		}

		interval = (interval - 1) / 3
	}

	return sorted_numbers
}
