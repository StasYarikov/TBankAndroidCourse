package firstTask

import firstTask.classes.Corgi
import firstTask.classes.Hasky
import firstTask.classes.Scotland
import firstTask.classes.Siamese
import firstTask.enums.TypeOfBehavior
import firstTask.enums.TypeOfBite
import firstTask.interfaces.Animal

class ZooStore(val animals: Array<Animal>) {

    fun breedAutentication(animal: Animal): String {
        return when (animal) {
            is Hasky -> "Эта собака породы Хаски"
            is Corgi -> "Эта собака породы Корги"
            is Scotland -> "Эта кошка породы Шотландская"
            is Siamese -> "Эта кошка породы Сиамская"
            else -> "Неизвестная порода"
        }
    }
}