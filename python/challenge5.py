import unittest
from math import floor


# https://oeis.org/A001951
# https://math.stackexchange.com/questions/2052179/how-to-find-sum-i-1n-left-lfloor-i-sqrt2-right-rfloor-a001951-a-beatty-s
def beatty_sequence_sum(n):
    sum_of_n = (n * (n + 1)) / 2

    if n == 0:
        return 0

    sqrt_2_decimals = 4142135623730950488016887242096980785696718753769480731766797379907324784621070388503875343276415727
    n_acc = int((sqrt_2_decimals * n) // (10 ** 100))

    sum_of_n_acc = (n_acc * (n_acc + 1)) / 2
    return n * n_acc + sum_of_n - sum_of_n_acc - beatty_sequence_sum(n_acc)


def solution(s):
    return str(beatty_sequence_sum(long(s)))


class Tests(unittest.TestCase):

    def test_1(self):
        self.assertEqual(solution("77"), "4208")

    def test_2(self):
        self.assertEqual(solution("5"), "19")

    def test_3(self):
        self.assertEqual(solution("1000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000012409"), "100")

if __name__ == '__main__':
    unittest.main()
