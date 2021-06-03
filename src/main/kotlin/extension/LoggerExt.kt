package extension

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.reflect.full.companionObject

/**
 * From https://stackoverflow.com/questions/34416869/idiomatic-way-of-logging-in-kotlin
 */
fun <R : Any> R.logger(): Lazy<Logger> {
    return lazy { LoggerFactory.getLogger(unwrapCompanionClass(this.javaClass).name) }
}

fun <T : Any> unwrapCompanionClass(ofClass: Class<T>): Class<*> {
    return ofClass.enclosingClass?.takeIf {
        ofClass.enclosingClass.kotlin.companionObject?.java == ofClass
    } ?: ofClass
}

fun Logger.trace(supplier: () -> String) {
    if (this.isTraceEnabled) {
        this.trace(supplier.invoke())
    }
}

fun Logger.debug(supplier: () -> String) {
    if (this.isDebugEnabled) {
        this.debug(supplier.invoke())
    }
}

fun Logger.info(supplier: () -> String) {
    if (this.isInfoEnabled) {
        this.info(supplier.invoke())
    }
}

fun Logger.warn(supplier: () -> String) {
    if (this.isWarnEnabled) {
        this.warn(supplier.invoke())
    }
}

fun Logger.error(supplier: () -> String) {
    if (this.isErrorEnabled) {
        this.error(supplier.invoke())
    }
}
