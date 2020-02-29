import unittest
from collections import defaultdict, namedtuple, deque
from copy import copy
from fractions import Fraction, gcd

QueueElement = namedtuple("QueueElement", ["ore_index", "fraction_to_reach", "parents"])


# a = base
# r = factor
def geo(a, r):
    return a / (1 - r)


def lcm(xs):
    return reduce(lambda x, y: x * y // gcd(x, y), xs)


def solution(m):
    terminals = []
    for i in range(0, len(m)):
        if len(filter(lambda x: x != 0, m[i])) == 0:
            terminals.append(i)

    cache = build_cache(m)

    values = defaultdict(lambda: 0)

    visited = {0}

    queue = deque([])
    queue.append(QueueElement(0, Fraction(1, 1), []))

    while len(queue) > 0:
        popped_value = queue.pop()

        current_ore = popped_value.ore_index
        x = m[current_ore]

        total_sum = sum(x)
        for j in range(0, len(x)):
            if x[j] != 0:
                current_factor = Fraction(x[j], total_sum)
                j_value = popped_value.fraction_to_reach * current_factor

                if j not in visited:
                    if j not in cache:
                        values[j] += j_value
                    else:
                        visited.add(j)
                        queue.append(QueueElement(j, j_value, []))

                else:
                    for entry in cache[j].items():
                        values[entry[0]] += geo(entry[1], j_value) - entry[1]

    lcm_values = lcm(map(lambda i: i.denominator, values.values()))
    result = list(map(lambda i: values[i].numerator * (lcm_values / values[i].denominator), terminals))
    result.append(lcm_values)
    return result


def build_cache(m):
    visited = {0}

    cache = defaultdict(lambda: defaultdict(lambda: 0))

    queue = deque([])
    queue.append(QueueElement(0, Fraction(1, 1), []))

    while len(queue) > 0:
        popped_value = queue.pop()

        current_ore = popped_value.ore_index
        x = m[current_ore]

        parents = popped_value.parents
        parents.append((current_ore, Fraction(1, 1)))

        total_sum = sum(x)
        for j in range(0, len(x)):
            if x[j] != 0:
                if j not in visited:
                    current_factor = Fraction(x[j], total_sum)

                    for parent in parents:
                        cache[parent[0]][j] += current_factor * parent[1]

                    parents_copied = map(lambda parent: (parent[0], parent[1] * current_factor), copy(parents))
                    queue.append(QueueElement(j, popped_value.fraction_to_reach * current_factor, parents_copied))

                    visited.add(j)

    return cache

# Tests
class Tests(unittest.TestCase):

    def test_1(self):
        self.assertEqual(
            solution([[0, 2, 1, 0, 0], [0, 0, 0, 3, 4], [0, 0, 0, 0, 0], [0, 0, 0, 0, 0], [0, 0, 0, 0, 0]]),
            [7, 6, 8, 21])

    def test_2(self):
        self.assertEqual(solution(
            [[0, 1, 0, 0, 0, 1], [4, 0, 0, 3, 2, 0], [0, 0, 0, 0, 0, 0], [0, 0, 0, 0, 0, 0], [0, 0, 0, 0, 0, 0],
             [0, 0, 0, 0, 0, 0]]), [0, 3, 2, 9, 14])

    def test_3(self):
        self.assertEqual(solution([[0, 1, 4], [0, 0, 0], [0, 0, 0]]), [1, 4, 5])

    def test_4(self):
        self.assertEqual(solution([[0, 1, 0, 0], [0, 0, 1, 0], [0, 0, 0, 1], [1, 1, 1, 1]]), [1, 2, 3, 4, 10])


if __name__ == '__main__':
    unittest.main()
