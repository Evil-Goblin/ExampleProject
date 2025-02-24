package playground.kotlinmultimodule.domain.post

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.ManyToOne
import playground.kotlinmultimodule.domain.account.Account
import playground.kotlinmultimodule.domain.jpacommon.BaseEntity

@Entity
class Post(
    @Column
    val title: String,
    @Column
    val content: String,
    @ManyToOne(fetch = FetchType.LAZY)
    val author: Account
): BaseEntity()
