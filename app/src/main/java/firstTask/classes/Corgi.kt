package firstTask.classes

import firstTask.enums.TypeOfBite
import firstTask.interfaces.Dog

class Corgi (
    override val weight: Double,
    override val typeOfBite: TypeOfBite,
    override val age: Int
) : Dog