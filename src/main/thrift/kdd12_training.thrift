namespace * com.inkenkun.x1.serialization.benchmark.thrift

struct AdRequest {
   1: i64    rowID,
   2: i32    clicks,
   3: i32    impression,
   4: string displayURL,
   5: i32    adID,
   6: i32    advertiserID,
   7: i16    depth,
   8: i16    position,
   9: i32    queryID,
  10: i32    keywordID,
  11: i32    titleID,
  12: i32    descriptionID,
  13: i32    userID
}

service AdRequestReceiver {
  void ping()
  i64 getRowId(1:AdRequest adreq)
}