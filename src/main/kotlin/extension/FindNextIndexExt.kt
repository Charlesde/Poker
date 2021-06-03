package extension

fun <T> List<T>.findNextIndex(inputIndex: Int) = this.indexOf(this[(inputIndex + 1).rem(this.size)])