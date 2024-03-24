if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED){
            if (sharedPreferences.getString("isSaved", "NO") == "YES"){

            }else{
                editor.putString("isSaved", "YES")
                editor.apply()
                contactViewModel.fetchSimContacts(this)
            }

        }else{
            Toast.makeText(this, "Please grant contact permission", Toast.LENGTH_SHORT).show()
        }
