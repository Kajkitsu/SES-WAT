
const functions = require('firebase-functions');
const admin = require('firebase-admin');

var serviceAccount = require('./serviceAccount.json');
admin.initializeApp({
	credential: admin.credential.cert(serviceAccount),
	databaseURL: "https://ses-wat.firebaseio.com"
  });

const db = admin.firestore();


exports.signToList = functions.https.onCall((data, context) =>{
    const code = data.code;
    const uid = context.auth.uid;

    return db.collection('list').doc(code).get()
    .then(snap =>{
        if(snap.exists){
            const isOpen = snap.data().open;
            if(isOpen){
                return db.collection('list').doc(code).collection('attendence').doc(uid).get()
                .then(snap => {
                    if(!snap.exists){
                        return db.collection('list').doc(code).collection('attendence').doc(uid).set({
                            confirmed : false,
                            timeOfAdd : admin.firestore.Timestamp.now(),
                            userID : uid
                        }).then(() => {
                            return {status : "Student został zapisany do listy"}
                        })
                    }
                    else{
                        return {status : "Student jest już zapisany do listy"}
                    }
                })
            }
            else{
                return {status : "Lista jest zamknięta"}
            }
        }
        else{
            return {status : "Lista nie istnieje"}
        }
    })
})

exports.addUserToFirebase = functions.https.onCall((data, context) =>{
    const uid = context.auth.uid;
	const userEmail = context.auth.token.email;
	const atIndex = userEmail.indexOf('@');
    const dotIndex = userEmail.indexOf('.');
    const teacherDomain = "wat.edu.pl";
    const studentDomain = "student.wat.edu.pl";
    var domain = userEmail.slice(atIndex + 1, userEmail.length )
	let userName = userEmail.slice(0, dotIndex);
    let userSurname = userEmail.slice(dotIndex+1, atIndex);
    userName = capitalizeFirstLetter(userName);
    userSurname = capitalizeFirstLetter(userSurname);
    console.log("Uruchomiono")
    let isTeacherB = false;
    if(domain === studentDomain){
        isTeacherB = false;
    }
    else if(domain === teacherDomain){
        isTeacherB = true;
    }
    return db.collection('users').doc(uid).set({
        name: userName,
        surname: userSurname,
        isTeacher: isTeacherB,
        userID : uid,
        email : userEmail
    }).then(()=>{
        console.log("Dodano")
        return true;
    }).catch(()=>{
        console.log("Błąd")
        return false;
    });
})

exports.createAttendanceList = functions.https.onCall((data, context) =>{
    const subjectName = data.name;
    const uid = context.auth.uid;
    let generatedCode = randomInt(100000,999999);
    console.log(generatedCode);
    let isTeacher = false;
    let _subjectID = "brak";
    let _subjectShortName = "brak";
    console.log(uid);
    return db.collection('users').doc(uid).get()
    .then(snapshot =>{
        console.log(snapshot.data());
        isTeacher = snapshot.data().isTeacher;
        if(isTeacher){
            return db.collection('subject').where("name", "==", subjectName).get()
            .then(snap =>{
                if(!snap.empty){
                    snap.forEach(doc =>{
                        _subjectID = doc.id;
                        _subjectShortName = doc.data().shortName;
                    })
                    return 0;
                }
                else{
                    return {status : "Nie ma takiego przedmiotu w bazie danych"}
                }
            }).then(() => {
                    return db.collection('list').doc(generatedCode.toString()).set({
                    code : generatedCode.toString(),
                    isOpen : false,
                    subjectID : _subjectID,
                    subjectShortName : _subjectShortName,
                    teacherID : uid,
                    startDate : admin.firestore.Timestamp.now()
                }).then(() =>{
                    return {status : "Lista została stworzona"}
                })
            })
        }
        else{
            return {status : "Użytkownik nie jest nauczycielem"}
        }
    })

});

exports.confirmStudent = functions.https.onCall((data, context) =>{
    const code = data.code;
    const studentID = data.studentID;
    const uid = context.auth.uid;

    return db.collection('list').where("code", "==", code).get()
    .then(snap =>{
        if(!snap.empty){
            return db.collection('list').doc(code).get()
            .then(snap =>{
                const teacherID = snap.data().teacherID;
                if(teacherID === uid){
                    return db.collection('list').doc(code).collection('attendence').doc(studentID).get()
                    .then(snap => {
                        if(snap.exists){
                            const confirmed = snap.data().confirmed;
                            if(confirmed){
                                return {status : "Student został już wcześniej zatwierdzony"}
                            }
                            else{
                                return db.collection('list').doc(code).collection('attendence').doc(studentID).update({
                                    confirmed : true
                                }).then(() =>{
                                    return {status : "Student został zatwierdzony"}
                                })
                            }
                        }
                        else{
                            return {status : "Student nie widnieje na liście"}
                        }
                    })
                }
                else{
                    return {status : "Nie możesz modyfikować cudzej listy"}
                }
            })
        }
        else{
            return {status : "Wybrana lista nie jestnieje"}
        }
    })
})

exports.cancelConfirmationStudent = functions.https.onCall((data, context) =>{
    const code = data.code;
    const studentID = data.studentID;
    const uid = context.auth.uid;

    return db.collection('list').where("code", "==", code).get()
    .then(snap =>{
        if(!snap.empty){
            return db.collection('list').doc(code).get()
            .then(snap =>{
                const teacherID = snap.data().teacherID;
                if(teacherID === uid){
                    return db.collection('list').doc(code).collection('attendence').doc(studentID).get()
                    .then(snap => {
                        if(snap.exists){
                            const confirmed = snap.data().confirmed;
                            if(!confirmed){
                                return {status : "Student już widnieje jako niezatwierdzony"}
                            }
                            else{
                                return db.collection('list').doc(code).collection('attendence').doc(studentID).update({
                                    confirmed : false
                                }).then(() =>{
                                    return {status : "Anulowano potwierdzenie obecności studenta"}
                                })
                            }
                        }
                        else{
                            return {status : "Student nie widnieje na liście"}
                        }
                    })
                }
                else{
                    return {status : "Nie możesz modyfikować cudzej listy"}
                }
            })
        }
        else{
            return {status : "Lista nie istnieje"}
        }
    })
})

exports.openList = functions.https.onCall((data, context) => {
    const code = data.code;
    const uid = context.auth.uid;
    return db.collection('list').where("code", "==", code).get()
    .then(snapshot => {
        if(!snapshot.empty){
            return db.collection('list').doc(code).get().then(snap =>{
                const teacherID = snap.data().teacherID;
                if(teacherID === uid){
                    const isOpen = snap.data().open;
                    if(!isOpen){
                        return db.collection('list').doc(code).update({
                            open : true,
                            startDate : admin.firestore.Timestamp.now()
                        }).then(() =>{
                            return {status : "Lista została otwarta"}
                        })
                    }
                    else{
                        return {status : "Lista jest już otwarta"}
                    }
                }
                else {
                    return {status : "Nie możesz modyfikować cudzej listy"}
                }
            });
        }
        else{
            return {status : "Lista nie istnieje"}
        }
    })
    
});


exports.closeList = functions.https.onCall((data, context) => {
    const code = data.code;
    const uid = context.auth.uid;
    return db.collection('list').where("code", "==", code).get()
    .then(snapshot => {
        if(!snapshot.empty){
            return db.collection('list').doc(code).get().then(snap =>{
                const teacherID = snap.data().teacherID;
                if(teacherID === uid){
                    const isOpen = snap.data().open;
                    if(isOpen){
                        return db.collection('list').doc(code).update({
                            open : false,
                            stopDate : admin.firestore.Timestamp.now()
                        }).then(() =>{
                            return {status : "Lista została zamknięta"}
                        })
                    }
                    else{
                        return {status : "Lista jest już zamknięta"}
                    }
                }
                else {
                    return {status : "Nie możesz modyfikować cudzej listy"}
                }
            });
        }
        else{
            return {status : "Lista nie istnieje"}
        }
    })
})

function capitalizeFirstLetter(string) {
    return string.charAt(0).toUpperCase() + string.slice(1);
}

function randomInt(low, high) {
    return Math.floor(Math.random() * (high - low) + low)
  }