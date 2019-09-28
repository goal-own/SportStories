package org.sportsstories.data.api.json_adapters

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import org.joda.time.DateTime

class DateTimeConverter {

    @ToJson
    fun toJson(dateTime: DateTime) = dateTime.toString()

    @FromJson
    fun fromJson(dateTimeString: String) = DateTime.parse(dateTimeString)

}
