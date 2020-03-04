namespace java com.didi.example.service

include "dto.thrift"
include "lib.thrift"

service UserService{
    dto.UserDto findById(1:i64 uid) throws (1: lib.MyException e)

    list<dto.UserDto> findByIds(1:list<i64> uids)

    i64 create(1:dto.UserDto user) throws (1: lib.MyException e)

    bool update(1:dto.UserDto user) throws (1: lib.MyException e)

    bool remove(1:i64 uid) throws (1: lib.MyException e)
}