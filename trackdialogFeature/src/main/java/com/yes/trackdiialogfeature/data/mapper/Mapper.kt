package com.yes.trackdiialogfeature.data.mapper

interface Mapper<DomainEntity,RepositoryEntity> {
    fun mapToDomain(repositoryEntity:ArrayList<RepositoryEntity> ):ArrayList<DomainEntity>
    fun matToRepository(domainEntity:ArrayList< DomainEntity>):ArrayList<RepositoryEntity>
}