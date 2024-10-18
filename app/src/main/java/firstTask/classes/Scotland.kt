package firstTask.classes

import firstTask.enums.TypeOfBehavior
import firstTask.interfaces.Cat

class Scotland(
    override val weight: Double,
    override val typeOfBehavior: TypeOfBehavior,
    override val age: Int
) : Cat