package extension

import org.testcontainers.containers.GenericContainer

// Need this extension as there is an issue with generics
// https://github.com/testcontainers/testcontainers-java/issues/318
class KGenericContainer(imageName: String) : GenericContainer<KGenericContainer>(imageName)
