import unittest
from fractions import Fraction, gcd


# File is based on
#   https://en.wikipedia.org/wiki/Absorbing_Markov_chain
#   https://github.com/mkutny/absorbing-markov-chains/blob/master/amc.py
def get_q_r(m):
    matrix_width = len(m[0])

    amount_of_terminals = next(x for x, val in enumerate(m) if sum(val) == 0)

    q = [m[i][0:amount_of_terminals] for i in range(0, amount_of_terminals)]
    r = [m[i][amount_of_terminals:matrix_width] for i in range(0, amount_of_terminals)]

    return q, r


def swap_rows_and_cols(m, i, j):
    n = []
    size = len(m)

    for r_i in range(size):
        new_row = []

        current_row = m[r_i]
        if r_i == i:
            current_row = m[j]
        elif r_i == j:
            current_row = m[i]

        for c_i in range(size):
            current_column = current_row[c_i]
            if c_i == i:
                current_column = current_row[j]
            elif c_i == j:
                current_column = current_row[i]

            new_row.append(current_column)
        n.append(new_row)

    return n


def group_terminals(m):
    size = len(m)

    previous_zero_r_i = -1
    for r_i in range(size):
        row_sum = sum(m[r_i])
        if row_sum == 0:
            previous_zero_r_i = r_i
        elif row_sum != 0 and previous_zero_r_i > -1:
            n = swap_rows_and_cols(m, r_i, previous_zero_r_i)
            return group_terminals(n)

    return m


def subtract_from_identity(q):
    for r_i in range(len(q)):
        for c_i in range(len(q)):
            if r_i == c_i:
                q[r_i][c_i] = Fraction(1, 1) - q[r_i][r_i]
            else:
                q[r_i][c_i] = -q[r_i][c_i]
    return q


def make_stochastic(m):
    for row in m:
        row_sum = sum(row)
        if row_sum > 0:
            for c_i in range(len(row)):
                row[c_i] = Fraction(row[c_i], row_sum)
    return m


def subtract_row_col(m, r_i, c_i):
    return [row[:c_i] + row[c_i + 1:] for row in (m[:r_i] + m[r_i + 1:])]


def lcm(xs):
    return reduce(lambda x, y: x * y // gcd(x, y), xs)


def multiply(a, b):
    m = []

    rows = len(a)
    cols = len(b[0])
    cols_to_sum = len(a[0])

    for r_i in range(rows):
        new_row = [
            sum(
                map(lambda i: a[r_i][i] * b[i][c_i], range(cols_to_sum))
            ) for c_i in range(cols)
        ]
        m.append(new_row)

    return m


def determinant(m):
    if len(m) == 2:
        return m[0][0] * m[1][1] - m[0][1] * m[1][0]

    d = 0
    for c in range(len(m)):
        d += ((-1) ** c) * m[0][c] * determinant(subtract_row_col(m, 0, c))

    return d


def inverse(m):
    d = determinant(m)

    if len(m) == 2:
        return [
            [Fraction(m[1][1], d), -1 * Fraction(m[0][1], d)],
            [-1 * Fraction(m[1][0], d), Fraction(m[0][0], d)]
        ]

    indices = range(len(m))

    # Create minor matrix
    minor_matrix = [[determinant(subtract_row_col(m, r_i, c_i)) for c_i in indices] for r_i in indices]

    # Multiply by checkerboard
    cof_matrix = [[minor_matrix[r_i][c_i] * ((-1) ** (r_i + c_i)) for c_i in indices] for r_i in indices]

    # Transpose
    transposed_cof_matrix = [[cof_matrix[c_i][r_i] for c_i in indices] for r_i in indices]

    # Multiply by 1/det
    inversed_matrix = [[Fraction(transposed_cof_matrix[r_i][c_i], d) for c_i in indices] for r_i in indices]

    return inversed_matrix


def solution(m):
    if len(m) == 1:
        return [1, 1]

    stochastic = make_stochastic(m)
    grouped_terminals = group_terminals(stochastic)

    (q, r) = get_q_r(grouped_terminals)

    s = subtract_from_identity(q)
    s_inv = inverse(s)
    b = multiply(s_inv, r)

    probabilities = b[0]
    lcm_computed = lcm(map(lambda i: i.denominator, probabilities))
    result = list(map(lambda i: i.numerator * (lcm_computed / i.denominator), probabilities))
    result.append(lcm_computed)
    return result


# Tests
class Tests(unittest.TestCase):

    def test_1(self):
        self.assertEqual([7, 6, 8, 21],
                         solution([[0, 2, 1, 0, 0], [0, 0, 0, 3, 4], [0, 0, 0, 0, 0], [0, 0, 0, 0, 0], [0, 0, 0, 0, 0]])
                         )

    def test_2(self):
        self.assertEqual([0, 3, 2, 9, 14], solution(
            [[0, 1, 0, 0, 0, 1], [4, 0, 0, 3, 2, 0], [0, 0, 0, 0, 0, 0], [0, 0, 0, 0, 0, 0], [0, 0, 0, 0, 0, 0],
             [0, 0, 0, 0, 0, 0]]))

    def test_3(self):
        self.assertEqual([1, 1], solution([[]]))


if __name__ == '__main__':
    unittest.main()
