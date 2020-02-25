def solution(n):
    return len(solutions(n, dict()))


def solutions(n, cache):
    if n in cache:
        return cache[n]

    left = 1
    right = n - 1

    n_subdivisions = set()

    while left < right:
        left_subdivisions = solutions(left, cache) if left >= 3 else set()
        right_subdivisions = solutions(right, cache) if right >= 3 else set()

        current_subdivisions = {(left, right)}
        current_subdivisions |= set(map(lambda it: it + (right,), left_subdivisions))
        current_subdivisions |= set(map(lambda it: (left,) + it, filter(lambda it: it[0] > left, right_subdivisions)))

        n_subdivisions |= current_subdivisions

        left += 1
        right -= 1

    cache[n] = n_subdivisions
    return n_subdivisions

# Tests
import unittest


class Tests(unittest.TestCase):

    # def test_1(self):
    #     self.assertEqual(solution(200), 487067745)

    def test_2(self):
        self.assertEqual(solution(3), 1)

    def test_3(self):
        self.assertEqual(solution(5), 2)

    def test_4(self):
        self.assertEqual(solution(6), 3)

    def test_5(self):
        self.assertEqual(solution(7), 4)

    def test_6(self):
        self.assertEqual(solution(50), 3657)

if __name__ == '__main__':
    unittest.main()
