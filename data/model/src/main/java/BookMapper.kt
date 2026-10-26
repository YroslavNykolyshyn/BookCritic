import com.bc.database.BookEntity
import com.bc.model.repository.BookData


fun BookEntity.toDomain() = BookData(
    id = id,
    title = title,
    description = description
)

fun BookData.toEntity() = BookEntity(
    id = id,
    title = title,
    isbn = null,
    coverPath = coverPath,
    description = description,
)
