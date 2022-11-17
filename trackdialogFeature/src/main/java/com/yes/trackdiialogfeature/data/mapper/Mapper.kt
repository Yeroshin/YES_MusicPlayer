package com.yes.trackdiialogfeature.data.mapper

interface Mapper<DomainEntity,RepositoryEntity> {
    fun mapToDomain(repositoryEntity:RepositoryEntity ):DomainEntity
    fun mapToRepository(domainEntity: DomainEntity):RepositoryEntity
}