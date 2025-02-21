
ALTER TABLE borrow_transactions DROP CONSTRAINT FK_borrow_transactions_borrower;

ALTER TABLE borrow_transactions DROP CONSTRAINT FK_borrow_transactions_works;

ALTER TABLE borrow_transactions ADD CONSTRAINT FK_borrow_transactions_borrower
    FOREIGN KEY (borrower_id)
        REFERENCES borrowers(borrower_id) ON DELETE RESTRICT ON UPDATE CASCADE;
ALTER TABLE borrow_transactions ADD CONSTRAINT FK_borrow_transactions_works
    FOREIGN KEY (work_id)
        REFERENCES works(work_id) ON DELETE RESTRICT ON UPDATE CASCADE;
