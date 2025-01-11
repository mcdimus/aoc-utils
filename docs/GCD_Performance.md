# Greatest Common Divisor (GCD) Performance

## Introduction

The greatest common divisor (GCD) of two integers is the largest positive integer that divides both numbers without a remainder. The GCD is a fundamental
concept in number theory and has many applications in computer science and mathematics. There are several algorithms for computing the GCD of two integers, each
with different performance characteristics.

## Euclidean Algorithm

The Euclidean algorithm is named after the ancient Greek mathematician Euclid, who first described the algorithm in his
book "Elements" around 300 BC.

The Euclidean algorithm is an efficient method for computing the GCD of two integers. The algorithm is based on the observation that the GCD of two numbers is
the same as the GCD of the smaller number and the difference between the two numbers. The algorithm works by repeatedly subtracting the smaller number from the
larger number until one of the numbers becomes zero. The GCD is then the non-zero number.

The Euclidean algorithm has a time complexity of `O(log(min(a, b)))`, where a and b are the two numbers.
This makes it one of the fastest algorithms for computing the GCD of two integers. The Euclidean algorithm is also easy to
implement and requires only basic arithmetic operations.

## Binary Algorithm

The binary algorithm, also known as Stein's algorithm, is an optimized version of the Euclidean algorithm that uses bitwise operations to compute the GCD of two
integers. The binary algorithm is more efficient than the Euclidean algorithm for large numbers, as it reduces the number of arithmetic operations required to
compute the GCD.

The binary algorithm has a time complexity of `O(log(a) * log(b))`, where a and b are the two numbers. This makes it faster than the Euclidean
algorithm for large numbers. The binary algorithm is more complex to implement than the Euclidean algorithm but can be more efficient in practice.

## Performance Comparison

<table>
  <thead>
  <tr>
    <th rowspan="2">Benchmark</th>
    <th>Euclidean</th>
    <th>Binary</th>
  </tr>
  <tr>
    <th>Throughput (ops/ms)</th>
    <th>Throughput (ops/ms)</th>
  </tr>
  </thead>
  <tbody>
  <tr>
    <td>GCDBenchmark.gcdOfFactorsOfTwoLongs</td>
    <td style="color: green;">1134.958 ± 5.606</td>
    <td> 505.423 ± 60.757</td>
  </tr>
  <tr>
    <td>GCDBenchmark.gcdOfFactorsOfTwoLongsReversed</td>
    <td style="color: green;">1936.640 ± 128.993</td>
    <td> 416.273 ± 4.346</td>
  </tr>
  <tr>
    <td>GCDBenchmark.gcdOfThousandLongs</td>
    <td>665.198 ± 6.206</td>
    <td style="color: green;">933.660 ± 35.564</td>
  </tr>
  <tr>
    <td>GCDBenchmark.gcdOfTwoLargeLong</td>
    <td>3023.703 ± 12.861</td>
    <td style="color: green;">9149.370 ± 163.633</td>
  </tr>
  <tr>
    <td>GCDBenchmark.gcdOfTwoSmallIntegers</td>
    <td>58951.078 ± 1732.376</td>
    <td style="color: green;">68618.344 ± 5588.257</td>
  </tr>
  </tbody>
</table>
