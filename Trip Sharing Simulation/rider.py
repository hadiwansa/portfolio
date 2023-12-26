"""
The rider module contains the Rider class. It also contains
constants that represent the status of the rider.

=== Constants ===
WAITING: A constant used for the waiting rider status.
CANCELLED: A constant used for the cancelled rider status.
SATISFIED: A constant used for the satisfied rider status
"""

from location import Location

WAITING = "waiting"
CANCELLED = "cancelled"
SATISFIED = "satisfied"


class Rider:

    """A rider for a ride-sharing service.

    """
    identifier: str
    patience: int
    origin: Location
    destination: Location
    status: str

    def __init__(self, identifier: str, patience: int, origin: Location,
                 destination: Location) -> None:
        """Initialize a Rider.

        """
        self.identifier = identifier
        self.patience = patience
        self.origin = origin
        self.destination = destination
        self.status = WAITING

    def __str__(self) -> str:
        """
        Return a string representation.
        """
        return f"{self.identifier}"

    def __eq__(self, other: object) -> bool:
        """
        Return True if self equals other, False otherwise.
        """
        if isinstance(other, Rider):
            return self.identifier == other.identifier
        return False

    def is_satisfied(self) -> None:
        """
        Return True if rider is satisfied.
        """
        self.status = SATISFIED

    def decrement_patience(self) -> None:
        self.patience -= 1
        if self.patience == 0:
            self.status = CANCELLED

    def is_cancelled(self) -> None:
        self.status = CANCELLED


if __name__ == '__main__':
    import python_ta
    python_ta.check_all(config={'extra-imports': ['location']})
