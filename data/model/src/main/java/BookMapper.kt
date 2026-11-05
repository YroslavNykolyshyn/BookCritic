import com.bc.database.BookEntity
import com.bc.model.repository.BookData
import com.bc.model.repository.ReadingStatus


fun BookEntity.toDomain() = BookData(
    id = id,
    title = title,
    author = author,
    status = ReadingStatus.valueOf(status),
    rating = rating,
    startedDate = startedDate,
    finishedDate = finishedDate,
    summary = summary,
    note = note,
    coverPath = coverPath,
    bookCost = bookCost,
    isFavorite = favorite ?: false,
)

fun BookData.toEntity() = BookEntity(
    id = id,
    title = title,
    author = author,
    status = status.name,
    rating = rating,
    startedDate = startedDate,
    finishedDate = finishedDate,
    summary = summary,
    note = note,
    coverPath = coverPath,
    bookCost = bookCost,
    favorite = isFavorite,
)
