package firstTask.classes

import firstTask.enums.TypeOfBite
import firstTask.interfaces.Dog

class Hasky(
    override val weight: Double,
    override val typeOfBite: TypeOfBite,
    override val age: Int
) : Dog