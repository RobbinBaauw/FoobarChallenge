import unittest
from math import log

cache = {}


def solution(n):
    n = long(n)

    if n in cache:
        return cache[n]

    if n <= 1:
        return 0

    closest_2_power = long(round(log(n) / log(2)))

    moving_to_power_cost = abs(2 ** closest_2_power - n) + closest_2_power
    if n % 2 != 0:
        adding_cost = 2 + solution((n + 1) / 2)
        minus_cost = 2 + solution((n - 1) / 2)

        total_cost = min(moving_to_power_cost, adding_cost, minus_cost)
    else:
        dividing_cost = 1 + solution(n / 2)
        total_cost = min(moving_to_power_cost, dividing_cost)

    cache[n] = total_cost
    return total_cost


# Tests
class Tests(unittest.TestCase):

    def test_1(self):
        self.assertEqual(5, solution('15'))

    def test_2(self):
        self.assertEqual(2, solution('4'))

    def test_3(self):
        self.assertEqual(1363, solution('24864614303596598796920782309286869334179515047412664361939260524782400586952573653427496778458408122213829317031586643370178179242347769424387121267005601410980150407734404624333036170522800562803603312477364455808169441147216360466057845743012443128764877808647238350865776243635470705613942345600623021221'))


if __name__ == '__main__':
    unittest.main()
