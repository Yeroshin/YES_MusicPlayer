package com.yes.trackdiialogfeature.data.mapper

interface Mapper<DomainEntity,RepositoryEntity> {
    fun mapToDomain(repositoryEntity:ArrayList<RepositoryEntity> ):DomainEntity
    fun matToRepository(domainEntity: DomainEntity):ArrayList<RepositoryEntity>
}