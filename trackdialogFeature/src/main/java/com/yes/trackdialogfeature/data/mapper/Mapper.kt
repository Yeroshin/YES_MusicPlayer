package com.yes.trackdialogfeature.data.mapper

interface Mapper<DomainEntity,RepositoryEntity> {
    fun mapToDomain(repositoryEntity:RepositoryEntity ):DomainEntity
    fun mapToRepository(domainEntity: DomainEntity):RepositoryEntity
}