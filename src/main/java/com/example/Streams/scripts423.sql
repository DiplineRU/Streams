SELECT faculties.name, students.faculty_id AS id, students.name, students.age
FROM faculties
         INNER JOIN students
                    ON faculties.id = students.faculty_id;

SELECT avatar.file_path,
       avatar.student_id as id,
       avatar.file_size,
       avatar.media_type,
       avatar.data,
       students.name,
       students.age
FROM avatar
         INNER JOIN students
                    ON avatar.student_id = students.id
WHERE avatar.file_path != NULL;