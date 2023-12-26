"""Dispatcher for the simulation"""

from typing import Optional
from driver import Driver
from rider import Rider


class Dispatcher:
    """A dispatcher fulfills requests from riders and drivers for a
    ride-sharing service.

    When a rider requests a driver, the dispatcher assigns a driver to the
    rider. If no driver is available, the rider is placed on a waiting
    list for the next available driver. A rider that has not yet been
    picked up by a driver may cancel their request.

    When a driver requests a rider, the dispatcher assigns a rider from
    the waiting list to the driver. If there is no rider on the waiting list
    the dispatcher does nothing. Once a driver requests a rider, the driver
    is registered with the dispatcher, and will be used to fulfill future
    rider requests.
    """

    def __init__(self) -> None:
        """Initialize a Dispatcher.

        """
        self.drivers = []
        self.waiting_list = []

    def __str__(self) -> str:
        """Return a string representation.

        """
        return f"Drivers available: {len(self.drivers)}, Riders waiting: " \
               f"{len(self.waiting_list)}"

    def request_driver(self, rider: Rider) -> Optional[Driver]:
        """Return a driver for the rider, or None if no driver is available.

        Add the rider to the waiting list if there is no available driver.

        """
        if len(self.drivers) > 0:
            driver = self.drivers.pop(0)
            return driver
        else:
            self.waiting_list.append(rider)
            return None

    def request_rider(self, driver: Driver) -> Optional[Rider]:
        """Return a rider for the driver, or None if no rider is available.

        If this is a new driver, register the driver for future rider requests.

        """
        if driver not in self.drivers:
            self.drivers.append(driver)

        if len(self.waiting_list) > 0:
            rider = self.waiting_list.pop(0)
            return rider
        else:
            return None

    def cancel_ride(self, rider: Rider) -> None:
        """Cancel the ride for rider.

        """
        if rider in self.waiting_list:
            self.waiting_list.remove(rider)


if __name__ == '__main__':
    import python_ta
    python_ta.check_all(config={'extra-imports': ['typing', 'driver', 'rider']})


