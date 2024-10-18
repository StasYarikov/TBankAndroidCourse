package firstTask

import firstTask.classes.Corgi
import firstTask.classes.Hasky
import firstTask.classes.Scotland
import firstTask.classes.Siamese
import firstTask.enums.TypeOfBehavior
import firstTask.enums.TypeOfBite
import firstTask.interfaces.Animal

fun main() {
    val animals: Array<Animal> = arrayOf(
        Hasky(35.8, TypeOfBite.STRAIGHT, 12),
        Hasky(30.8, TypeOfBite.OVERBITE, 6),
        Hasky(12.3, TypeOfBite.UNDERBITE, 1),
        Corgi(20.1, TypeOfBite.UNDERBITE, 1),
        Corgi(18.4, TypeOfBite.UNDERBITE, 2),
        Corgi(15.8, TypeOfBite.STRAIGHT, 3),
        Scotland(10.8, TypeOfBehavior.ACTIVE, 11),
        Scotland(8.9, TypeOfBehavior.PASSIVE, 1),
        Scotland(12.4, TypeOfBehavior.PASSIVE, 5),
        Siamese(7.2, TypeOfBehavior.ACTIVE, 6),
        Siamese(6.3, TypeOfBehavior.ACTIVE, 3),
        Siamese(5.9, TypeOfBehavior.ACTIVE, 4)
    )

    val zooStore = ZooStore(animals)
    for (animal in zooStore.animals) {
        println(zooStore.breedAutentication(animal))
    }
}