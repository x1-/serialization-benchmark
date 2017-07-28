namespace * com.inkenkun.x1.serialization.benchmark.thrift

struct AdRequestNullable {
   1: required i64    rowID,
   2: optional i32    clicks,
   3: optional i32    impression,
   4: optional string displayURL,
   5: optional i32    adID,
   6: optional i32    advertiserID,
   7: optional i16    depth,
   8: optional i16    position,
   9: optional i32    queryID,
  10: optional i32    keywordID,
  11: optional i32    titleID,
  12: optional i32    descriptionID,
  13: optional i32    userID
}

service AdRequestNullableReceiver {
  void ping(),
  i64 getRowId(1:AdRequestNullable adreq)
}