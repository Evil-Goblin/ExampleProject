package playground.kotlinmultimodule.domain.account

import playground.kotlinmultimodule.domain.jpacommon.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity

@Entity
class Account(
    @Column
    val email: String,
    @Column
    val name: String,
    @Column
    val password: String
): BaseEntity()
