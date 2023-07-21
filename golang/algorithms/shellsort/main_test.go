package main

import (
	"math/rand"
	"testing"
)

func BenchmarkPrimeNumbers(b *testing.B) {

	for i := 0; i < b.N; i++ {
		var numbers = rand.Perm(1000 * 1000 * 1000)
		shell_sort(numbers)
	}
}
