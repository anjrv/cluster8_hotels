CREATE TABLE HOTELS (
    Name VARCHAR(15) NOT NULL COLLATE NOCASE
    , Region INT NOT NULL
    , Address VARCHAR(30) COLLATE NOCASE
    , Image VARCHAR(10)
    , Accessibility INT
    , Gym INT
    , Spa INT
    , PRIMARY KEY(name
));

CREATE TABLE ROOMS (
    Rnumber INT NOT NULL
    , Hname VARCHAR(15) NOT NULL COLLATE NOCASE
    , Price INT NOT NULL
    , Wifi INT 
    , Breakfast INT
    , Beds INT NOT NULL
    , Adults INT NOT NULL
    , Children INT
    , PRIMARY KEY(Rnumber,Hname)
    , FOREIGN KEY(Hname) REFERENCES HOTELS(Name
));

CREATE TABLE RESERVATIONS (
    ReservationID VARCHAR(20) NOT NULL
    , CreateDate BIGINT NOT NULL
    , StartDate BIGINT NOT NULL
    , EndDate BIGINT NOT NULL
    , Cancelled INT DEFAULT 0
    , Paid INT DEFAULT 0
    , Contact VARCHAR(30) NOT NULL
    , Hname VARCHAR(15) NOT NULL COLLATE NOCASE
    , Rnumber INT NOT NULL
    , PRIMARY KEY(ReservationID)
    , FOREIGN KEY(Hname,Rnumber) REFERENCES ROOMS(Hname,Rnumber)
);

CREATE TABLE REVIEWS (
    Grade INT NOT NULL
    , Hname VARCHAR(15) NOT NULL COLLATE NOCASE
    , Rnumber INT NOT NULL
    , Text VARCHAR(280)
    , ResID VARCHAR(20) NOT NULL
    , PRIMARY KEY(ResID)
    , FOREIGN KEY(ResID) REFERENCES RESERVATIONS(ReservationID)
    , FOREIGN KEY(Hname,Rnumber) REFERENCES ROOMS(Hname,Rnumber)
);

INSERT INTO HOTELS VALUES('The Plaza Hotel',0,'768 Fifth Avenue','plaza.jpg',0,1,1);
INSERT INTO HOTELS VALUES('Bates Motel',0,'1054 272nd Street','bates.jpg',0,0,0);
INSERT INTO HOTELS VALUES('Park Hyatt',1,'3 Chome-7-1-2 Nishishinjuku','hyatt.jpg',1,1,1);
INSERT INTO HOTELS VALUES('The Overlook Hotel',2,'333 E Wonderview Ave','overlook.jpg',0,0,1);
INSERT INTO HOTELS VALUES("Caesar's Palace",3,'3570 S Las Vegas Blvd','caesar.jpg',1,0,1);
INSERT INTO HOTELS VALUES('Grand Budapest Hotel',4,'Görlitzer Warenhaus Department Store','budapest.jpg',0,0,1);
INSERT INTO HOTELS VALUES('Seminole Ritz Hotel',5,'1500 Orange Ave','seminole.jpg',1,1,1);
INSERT INTO HOTELS VALUES('Beverly Wilshire',6,'9500 Wilshire Blvd','wilshire.jpg',1,0,1);

INSERT INTO ROOMS VALUES(101,'The Plaza Hotel',20000,1,0,1,1,0);
INSERT INTO ROOMS VALUES(102,'The Plaza Hotel',25000,1,0,2,1,1);
INSERT INTO ROOMS VALUES(103,'The Plaza Hotel',28000,1,1,1,2,0);

INSERT INTO ROOMS VALUES(101,'Bates Motel',10000,0,0,1,1,0);
INSERT INTO ROOMS VALUES(102,'Bates Motel',12000,0,0,1,1,0);
INSERT INTO ROOMS VALUES(103,'Bates Motel',15000,1,0,1,1,0);

INSERT INTO ROOMS VALUES(101,'Park Hyatt',30000,1,0,2,2,0);
INSERT INTO ROOMS VALUES(102,'Park Hyatt',30000,1,0,2,1,1);
INSERT INTO ROOMS VALUES(103,'Park Hyatt',35000,1,1,1,2,0);

INSERT INTO ROOMS VALUES(101,'The Overlook Hotel',20000,0,0,2,1,1);
INSERT INTO ROOMS VALUES(102,'The Overlook Hotel',22000,0,0,3,2,1);
INSERT INTO ROOMS VALUES(103,'The Overlook Hotel',25000,1,0,1,2,0);

INSERT INTO ROOMS VALUES(101,"Caesar's Palace",30000,1,0,1,1,0);
INSERT INTO ROOMS VALUES(102,"Caesar's Palace",30000,1,0,1,1,0);
INSERT INTO ROOMS VALUES(103,"Caesar's Palace",35000,1,0,1,2,0);

INSERT INTO ROOMS VALUES(101,'Grand Budapest Hotel',25000,0,0,1,2,0);
INSERT INTO ROOMS VALUES(102,'Grand Budapest Hotel',25000,0,0,1,2,0);
INSERT INTO ROOMS VALUES(103,'Grand Budapest Hotel',28000,1,0,2,2,1);

INSERT INTO ROOMS VALUES(101,'Seminole Ritz Hotel',40000,1,0,1,2,0);
INSERT INTO ROOMS VALUES(102,'Seminole Ritz Hotel',45000,1,0,2,2,1);
INSERT INTO ROOMS VALUES(103,'Seminole Ritz Hotel',50000,1,1,2,2,1);

INSERT INTO ROOMS VALUES(101,'Beverly Wilshire',30000,1,0,1,2,0);
INSERT INTO ROOMS VALUES(102,'Beverly Wilshire',30000,1,0,2,2,1);
INSERT INTO ROOMS VALUES(103,'Beverly Wilshire',35000,1,1,2,2,1);
