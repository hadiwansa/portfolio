"""
Assignment 2: Trees for Treemap

=== CSC148 Summer 2023 ===
This code is provided solely for the personal and private use of
students taking the CSC148 course at the University of Toronto.
Copying for purposes other than this use is expressly prohibited.
All forms of distribution of this code, whether as given or with
any changes, are expressly prohibited.

All of the files in this directory and all subdirectories are:
Copyright (c) 2023 Bogdan Simion, David Liu, Diane Horton,
                   Haocheng Hu, Jacqueline Smith, Andrea Mitchell,
                   Bahar Aameri

=== Module Description ===
This module contains the basic tree interface required by the treemap
visualiser. You will both add to the abstract class, and complete a
concrete implementation of a subclass to represent files and folders on your
computer's file system.
"""
from __future__ import annotations

import math
import os
from random import randint
from typing import List, Tuple, Optional


def get_colour() -> Tuple[int, int, int]:
    """This function picks a random colour selectively such that it is not on
    the grey scale. The colour is close to the grey scale if the r g b values
    have a small variance. This function checks if all the numbers are close to
    the mean, if so, it shifts the last digit by 150.

    This way you can't confuse the leaf rectangles with folder rectangles,
    because the leaves will always be a colour, never close to black / white.
    """
    rgb = [randint(0, 255), randint(0, 255), randint(0, 255)]
    avg = sum(rgb) // 3
    count = 0
    for item in rgb:
        if abs(item - avg) < 20:
            count += 1
    if count == 3:
        rgb[2] = (rgb[2] + 150) % 255
    return tuple(rgb)


class TMTree:
    """A TreeMappableTree: a tree that is compatible with the treemap
    visualiser.

    This is an abstract class that should not be instantiated directly.

    You may NOT add any attributes, public or private, to this class.
    However, part of this assignment will involve you implementing new public
    *methods* for this interface.
    You should not add any new public methods other than those required by
    the client code.
    You can, however, freely add private methods as needed.

    === Public Attributes ===
    rect: The pygame rectangle representing this node in the visualization.
    data_size: The size of the data represented by this tree.

    === Private Attributes ===
    _colour: The RGB colour value of the root of this tree.
    _name: The root value of this tree, or None if this tree is empty.
    _subtrees: The subtrees of this tree.
    _parent_tree: The parent tree of this tree; i.e., the tree that contains
    this tree as a subtree, or None if this tree is not part of a larger tree.
    _expanded: Whether this tree is considered expanded for visualization.
    _depth: The depth of this tree node in relation to the root.

    === Representation Invariants ===
    - data_size >= 0
    - If _subtrees is not empty, then data_size is equal to the sum of the
      data_size of each subtree.
    - _colour's elements are each in the range 0-255.
    - If _name is None, then _subtrees is empty, _parent_tree is None, and
      data_size is 0.
      This setting of attributes represents an empty tree.
    - if _parent_tree is not None, then self is in _parent_tree._subtrees
    - if _expanded is True, then _parent_tree._expanded is True
    - if _expanded is False, then _expanded is False for every tree
      in _subtrees
    - if _subtrees is empty, then _expanded is False
    """

    rect: Tuple[int, int, int, int]
    data_size: int
    _colour: Tuple[int, int, int]
    _name: str
    _subtrees: List[TMTree]
    _parent_tree: Optional[TMTree]
    _expanded: bool
    _depth: int

    def __init__(self, name: str, subtrees: List[TMTree],
                 data_size: int = 0) -> None:
        """Initializes a new TMTree with a random colour, the provided name
        and sets the subtrees to the list of provided subtrees. Sets this tree
        as the parent for each of its subtrees.

        Precondition: if <name> is None, then <subtrees> is empty.
        """
        self.rect = (0, 0, 0, 0)
        self._parent_tree = None
        self._depth = 0
        self._name = name
        self._colour = get_colour() if name is not None else (0, 0, 0)
        self._subtrees = subtrees if name is not None else []
        self._expanded = False

        if self._name is None:
            self.data_size = 0

        if len(subtrees) > 0:
            self.data_size = sum([subtree.data_size for subtree in subtrees])
        else:
            self.data_size = data_size

        for subtree in subtrees:
            subtree._parent_tree = self

    def is_empty(self) -> bool:
        """Returns True iff this tree is empty.
        """
        return self._name is None

    def get_parent(self) -> Optional[TMTree]:
        """Returns the parent of this tree.
        """
        return self._parent_tree

    # **************************************************************************
    # ************* TASK 2: UPDATE AND GET RECTANGLES **************************
    # **************************************************************************

    def update_rectangles(self, rect: Tuple[int, int, int, int]) -> None:
        """Updates the rectangles in this tree and its descendants using the
        treemap algorithm to fill the area defined by the <rect> parameter.
        """
        x, y, width, height = rect

        if self.data_size == 0:
            self.rect = (0, 0, 0, 0)
            return

        self.rect = rect
        curr_x = x
        curr_y = y

        if width > height:
            for i, subtree in enumerate(self._subtrees):
                percent = subtree.data_size / self.data_size
                new_width = width * percent if i != len(self._subtrees) - 1 \
                    else width + x - curr_x
                new_width = math.floor(new_width)
                subtree.update_rectangles((curr_x, y, new_width, height))
                curr_x += new_width
        else:
            for i, subtree in enumerate(self._subtrees):
                percent = subtree.data_size / self.data_size
                new_height = height * percent if i != len(self._subtrees) - 1 \
                    else height + y - curr_y
                new_height = math.floor(new_height)
                subtree.update_rectangles((x, curr_y, width, new_height))
                curr_y += new_height

    def get_rectangles(self) -> List[Tuple[Tuple[int, int, int, int],
    Tuple[int, int, int]]]:
        """Returns a list with tuples for every leaf in the displayed-tree
        rooted at this tree. Each tuple consists of a tuple that defines the
        appropriate pygame rectangle to display for a leaf, and the colour
        to fill it with.
        """
        if not self._expanded or not self._subtrees:
            return [(self.rect, self._colour)]
        else:
            return [rect for subtree in self._subtrees for rect in
                    subtree.get_rectangles()]

    # **************************************************************************
    # **************** TASK 3: GET_TREE_AT_POSITION ****************************
    # **************************************************************************

    def get_tree_at_position(self, pos: Tuple[int, int]) -> Optional[TMTree]:
        """Returns the leaf in the displayed-tree rooted at this tree whose
        rectangle contains position <pos>, or None if <pos> is outside of this
        tree's rectangle.

        If <pos> is on the shared edge between two or more rectangles,
        always return the leftmost and topmost rectangle (wherever applicable).
        """
        x, y, w, h = self.rect
        pos_x, pos_y = pos

        if not (x <= pos_x < x + w and y <= pos_y < y + h):
            return None

        if not self._expanded or not self._subtrees:
            return self

        for subtree in self._subtrees:
            result = subtree.get_tree_at_position(pos)
            if result:
                return result

        return None

    # **************************************************************************
    # ********* TASK 4: MOVE, CHANGE SIZE, DELETE, UPDATE SIZES ****************
    # **************************************************************************

    def update_data_sizes(self) -> int:
        """Updates the data_size attribute for this tree and all its subtrees,
        based on the size of their leaves, and return the new size of the given
        tree node after updating.

        If this tree is a leaf, return its size unchanged.
        """
        if not self._expanded or not self._subtrees:
            return self.data_size

        self.data_size = sum(
            subtree.update_data_sizes() for subtree in self._subtrees)
        return self.data_size

    def change_size(self, factor: float) -> None:
        """Changes the value of this tree's data_size attribute by <factor>.
        Always rounds up the amount to change, so that it's an int, and
        some change is made. If the tree is not a leaf, this method does
        nothing.
        """
        if not self._expanded or not self._subtrees:
            change = self.data_size * factor
            change = math.ceil(change) if change > 0 else math.floor(change)

            self.data_size += change

            if self.data_size < 1:
                self.data_size = 1

    def delete_self(self) -> bool:
        """Removes the current node from the visualization and
        returns whether the deletion was successful. Only do this if this node
        has a parent tree.

        Do not set self._parent_tree to None, because it might be used
        by the visualizer to go back to the parent folder.
        """
        if self._parent_tree is None:
            return False

        self._parent_tree._subtrees.remove(self)

        if not self._parent_tree._subtrees and self._parent_tree._parent_tree:
            self._parent_tree.delete_self()

        return True

    # **************************************************************************
    # ************* TASK 5: UPDATE_COLOURS_AND_DEPTHS **************************
    # **************************************************************************

    def update_depths(self) -> None:
        """Updates the depths of the nodes, starting with a depth of 0 at this
        tree node.
        """
        for subtree in self._subtrees:
            subtree._depth = self._depth + 1
            subtree.update_depths()

    def max_depth(self) -> int:
        """Returns the maximum depth of the tree, which is the maximum length
        between a leaf node and the root node.
        """
        if not self._subtrees:
            return self._depth

        return max(subtree.max_depth() for subtree in self._subtrees)

    def update_colours(self, step_size: int) -> None:
        """Updates the colours so that the internal tree nodes are
        shades of grey depending on their depth. The root node will be black
        (0, 0, 0) and all internal nodes will be shades of grey depending on
        their depth, where the step size determines the shade of grey.
        Leaf nodes should not be updated.
        """
        if self._subtrees:
            shade = self._depth * step_size
            self._colour = (shade, shade, shade)

            for subtree in self._subtrees:
                subtree.update_colours(step_size)

    def update_colours_and_depths(self) -> None:
        """This method is called any time the tree is manipulated or right after
        instantiation. Updates the _depth and _colour attributes throughout
        the tree.
        """
        self.update_depths()

        max_d = self.max_depth()

        step_size = 200 // (max_d - 1 if max_d != 0 else 1)

        self.update_colours(step_size)

    # **************************************************************************
    # ********* TASK 6: EXPAND, COLLAPSE, EXPAND ALL, COLLAPSE ALL *************
    # **************************************************************************

    def expand(self) -> None:
        """Sets this tree to be expanded. But not if it is a leaf.
        """
        if self._subtrees:
            self._expanded = True

    def expand_all(self) -> None:
        """Sets this tree and all its descendants to be expanded, apart from the
        leaf nodes.
        """
        self.expand()

        for subtree in self._subtrees:
            subtree.expand_all()

    def collapse(self) -> None:
        """Collapses the parent tree of the given tree node and also collapse
        all of its descendants.
        """
        if self._parent_tree is not None:
            self._parent_tree._expanded = False

        for subtree in self._subtrees:
            subtree.collapse()

    def collapse_all(self) -> None:
        """ Collapses ALL nodes in the tree.
        """
        self.collapse()
        if self._parent_tree is not None:
            self._parent_tree.collapse_all()

        self._expanded = False

    # **************************************************************************
    # ************* TASK 7 : DUPLICATE MOVE COPY_PASTE *************************
    # **************************************************************************

    def move(self, destination: TMTree) -> None:
        """If this tree is a leaf, and <destination> is not a leaf, moves this
        tree to be the last subtree of <destination>. Otherwise, does nothing.
        """
        if not self._subtrees and destination._subtrees:
            if self._parent_tree:
                self._parent_tree._subtrees.remove(self)

            destination._subtrees.append(self)
            self._parent_tree = destination

    def duplicate(self) -> Optional[TMTree]:
        """Duplicates the given tree, if it is a leaf node. It stores
        the new tree with the same parent as the given leaf. Returns the
        new node. If the given tree is not a leaf, does nothing.
        """
        if not self._subtrees:
            new_node = FileSystemTree(self.get_full_path())
            self._parent_tree._subtrees.append(new_node)
            new_node._parent_tree = self._parent_tree
            return new_node
        return None

    def copy_paste(self, destination: TMTree) -> None:
        """If this tree is a leaf, and <destination> is not a leaf, this method
        copies the given, and moves the copy to the last subtree of
        <destination>. Otherwise, does nothing.
        """
        if not self._subtrees and destination._subtrees:
            copy_node = FileSystemTree(self.get_full_path())
            destination._subtrees.append(copy_node)
            copy_node._parent_tree = destination

    # **************************************************************************
    # ************* HELPER FUNCTION FOR TESTING PURPOSES  **********************
    # **************************************************************************
    def tree_traversal(self) -> List[Tuple[str, int, Tuple[int, int, int]]]:
        """For testing purposes to see the depth and colour attributes for each
        internal node in the tree. Used for passing test case 5.
        """
        if len(self._subtrees) > 0:
            output_list = [(self._name, self._depth, self._colour)]
            for tree in self._subtrees:
                output_list += tree.tree_traversal()
            return output_list
        else:
            return []

    # **************************************************************************
    # *********** METHODS DEFINED FOR STRING REPRESENTATION  *******************
    # **************************************************************************
    def get_path_string(self) -> str:
        """Return a string representing the path containing this tree
        and its ancestors, using the separator for this OS between each
        tree's name.
        """
        if self._parent_tree is None:
            return self._name
        else:
            return self._parent_tree.get_path_string() + \
                self.get_separator() + self._name

    def get_separator(self) -> str:
        """Returns the string used to separate names in the string
        representation of a path from the tree root to this tree.
        """
        raise NotImplementedError

    def get_suffix(self) -> str:
        """Returns the string used at the end of the string representation of
        a path from the tree root to this tree.
        """
        raise NotImplementedError

    # **************************************************************************
    # **************** HELPER FUNCTION FOR TASK 7  *****************************
    # **************************************************************************
    def get_full_path(self) -> str:
        """Returns the path attribute for this tree.
        """
        raise NotImplementedError


class FileSystemTree(TMTree):
    """A tree representation of files and folders in a file system.

    The internal nodes represent folders, and the leaves represent regular
    files (e.g., PDF documents, movie files, Python source code files, etc.).

    The _name attribute stores the *name* of the folder or file, not its full
    path. E.g., store 'assignments', not '/Users/Diane/csc148/assignments'

    The data_size attribute for regular files is simply the size of the file,
    as reported by os.path.getsize.

    === Private Attributes ===
    _path: the path that was used to instantiate this tree.
    """
    _path: str

    def __init__(self, my_path: str) -> None:
        """Stores the directory given by <my_path> into a tree data structure
        using the TMTree class.

        Precondition: <my_path> is a valid path for this computer.
        """
        self._path = my_path
        name = os.path.basename(my_path)

        if os.path.isdir(my_path):
            subtrees = [FileSystemTree(os.path.join(my_path, item)) for item in
                        os.listdir(my_path)]
            tot_size = sum([subtree.data_size for subtree in subtrees])
            TMTree.__init__(self, name, subtrees, data_size=tot_size)
        else:
            size = os.path.getsize(my_path)
            TMTree.__init__(self, name, [], data_size=size)

    def get_full_path(self) -> str:
        """Returns the file path for the tree object.
        """
        return self._path

    def get_separator(self) -> str:
        """Returns the file separator for this OS.
        """
        return os.sep

    def get_suffix(self) -> str:
        """Returns the final descriptor of this tree.
        """

        def convert_size(data_size: float, suffix: str = 'B') -> str:
            suffixes = {'B': 'kB', 'kB': 'MB', 'MB': 'GB', 'GB': 'TB'}
            if data_size < 1024 or suffix == 'TB':
                return f'{data_size:.2f}{suffix}'
            return convert_size(data_size / 1024, suffixes[suffix])

        components = []
        if len(self._subtrees) == 0:
            components.append('file')
        else:
            components.append('folder')
            components.append(f'{len(self._subtrees)} items')
        components.append(convert_size(self.data_size))
        return f' ({", ".join(components)})'


if __name__ == '__main__':
    import python_ta

    python_ta.check_all(config={
        'allowed-import-modules': [
            'python_ta', 'typing', 'math', 'random', 'os', '__future__'
        ]
    })
