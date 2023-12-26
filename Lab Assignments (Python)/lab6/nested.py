"""Lab 6: Recursion

=== CSC148 Summer 2023 ===
Department of Mathematical and Computational Sciences,
University of Toronto Mississauga

=== Module Description ===
This module contains a few nested list functions for you to practice recursion.
"""
from typing import Union


def add_n(obj: Union[int, list], n: int) -> Union[int, list]:
    """Return a new nested list where <n> is added to every item in <obj>.

    >>> add_n(10, 3)
    13
    >>> add_n([1, 2, [1, 2], 4], 10)
    [11, 12, [11, 12], 14]
    """

    if isinstance(obj, int):
        return obj + n
    else:
        return [add_n(sublist, n) for sublist in obj]


def nested_list_equal(obj1: Union[int, list], obj2: Union[int, list]) -> bool:
    """Return whether two nested lists are equal, i.e., have the same value.

    Note: order matters.

    >>> nested_list_equal(17, [1, 2, 3])
    False
    >>> nested_list_equal([1, 2, [1, 2], 4], [1, 2, [1, 2], 4])
    True
    >>> nested_list_equal([1, 2, [1, 2], 4], [4, 2, [2, 1], 3])
    False
    """
    if isinstance(obj1, int) and isinstance(obj2, int):
        return obj1 == obj2
    elif isinstance(obj1, list) and isinstance(obj2, list) and len(obj1) == len(obj2):
        for i in range(len(obj1)):
            if not nested_list_equal(obj1[i], obj2[i]):
                return False
            return True
    else:
        return False


def duplicate(obj: Union[int, list]) -> Union[int, list]:
    """Return a new nested list with all numbers in <obj> duplicated.

    Each integer in <obj> should appear twice *consecutively* in the
    output nested list. The nesting structure is the same as the input,
    only with some new numbers added. See doctest examples for details.

    If <obj> is an int, return a list containing two copies of it.

    >>> duplicate(1)
    [1, 1]
    >>> duplicate([])
    []
    >>> duplicate([1, 2])
    [1, 1, 2, 2]
    >>> duplicate([1, [2, 3]])  # NOT [1, 1, [2, 2, 3, 3], [2, 2, 3, 3]]
    [1, 1, [2, 2, 3, 3]]
    """

    if isinstance(obj, int):
        return [obj, obj]
    else:
        dup_list = []
        for sublist in obj:
            if isinstance(sublist, int):
                dup_list.append(sublist)
                dup_list.append(sublist)
            else:
                dup_list.append(duplicate(sublist))
        return dup_list


if __name__ == '__main__':
    import doctest
    doctest.testmod()

    # import python_ta
    # python_ta.check_all()
