import com.bc.database.BookEntity
import com.bc.database.BookWithGenre
import com.bc.database.genre.BookGenreEntity
import com.bc.model.repository.BookData
import com.bc.model.repository.BookGenre
import com.bc.model.repository.ReadingStatus

fun BookWithGenre.toDomain() = book.toDomain(genre = genre?.toDomain())

fun BookEntity.toDomain(genre: BookGenre?)   = BookData(
    id = id,
    title = title,
    author = author,
    genre = genre,
    status = ReadingStatus.fromValue(status),
    rating = rating,
    startedDate = startedDate,
    finishedDate = finishedDate,
    summary = summary,
    note = note,
    coverPath = coverPath,
    bookCost = bookCost,
    isFavorite = favorite ?: false,
    totalPages = totalPages,
    currentPage = currentPage,
)

fun BookData.toEntity() = BookEntity(
    id = id,
    title = title,
    author = author,
    genreId = genre?.id,
    status = status.value,
    rating = rating,
    startedDate = startedDate,
    finishedDate = finishedDate,
    summary = summary,
    note = note,
    coverPath = coverPath,
    bookCost = bookCost,
    favorite = isFavorite,
    totalPages = totalPages,
    currentPage = currentPage,
)

fun BookGenreEntity.toDomain() = BookGenre(
    id = id,
    name = name,
)

fun BookGenre.toEntity() = BookGenreEntity(
    id = id,
    name = name,
)
